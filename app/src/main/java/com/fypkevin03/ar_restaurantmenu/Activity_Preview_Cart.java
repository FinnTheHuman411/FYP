package com.fypkevin03.ar_restaurantmenu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Activity_Preview_Cart extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {

    private ArFragment arFragment;
    private Renderable model;
    BottomSheetDialog configWindow;
    private float modelScale = 1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().addFragmentOnAttachListener(this);
        configWindow = new BottomSheetDialog(this);
        createConfigWin();

        configWindow.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Load model.glb from assets folder or http url
//        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
//        arFragment.setOnTapPlaneGlbModel(food_model);
//        arFragment.setOnTapPlaneGlbModel("https://github.com/codemaker2015/3d-models/blob/master/glb_files/model.glb?raw=true");
        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener(null);

        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);
    }

    public void loadModels(String food_model) {
        WeakReference<Activity_Preview_Cart> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse(food_model))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    Activity_Preview_Cart activity = weakActivity.get();
                    if (activity != null) {
                        activity.model = model;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (model == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable model and add it to the anchor.
        TransformableNode foodNode = new TransformableNode(arFragment.getTransformationSystem());
        foodNode.setLocalScale(new Vector3(modelScale,modelScale,modelScale));
        foodNode.setParent(anchorNode);
        foodNode.setRenderable(this.model)
                .animate(true).start();
        foodNode.getScaleController().setEnabled(false);
        foodNode.getRenderableInstance().setCulling(false);
        foodNode.select();

    }

    private void createConfigWin() {
        View view = getLayoutInflater().inflate(R.layout.bottom_preview_cart_config,null,false);
        configWindow.setContentView(view);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<String> food_models = new ArrayList<String>();
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();

        listView = view.findViewById(R.id.cartListview);

        if (resultSet.getCount() != 0){
            do{
                values.add(resultSet.getString(3) + " x" + resultSet.getString(5));
                food_models.add(resultSet.getString(4));
            } while (resultSet.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadModels(food_models.get(position));
            }
        });
    }

    public void showConfig(View v) {
        configWindow.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
