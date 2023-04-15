package com.fypkevin03.ar_restaurantmenu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.List;
import java.util.Objects;

public class Activity_Preview_Mode extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {

    private ArFragment arFragment;
    private Renderable model;
    BottomSheetDialog configWindow;
    private float modelScale = 1;
    private String foodSize = "M";
    private String product_model = "";

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

        final String food_model = getIntent().getStringExtra("product_model");
        product_model = food_model;

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

        loadModels(food_model);
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
        WeakReference<Activity_Preview_Mode> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse(food_model))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    Activity_Preview_Mode activity = weakActivity.get();
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
        View view = getLayoutInflater().inflate(R.layout.bottom_preview_mode_config,null,false);
        configWindow.setContentView(view);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.sizeSelect);
        Button clearModel = (Button) view.findViewById(R.id.clearModel);
        Button addToCart = (Button) view.findViewById(R.id.addToCart);
        EditText noteEdit = (EditText) view.findViewById(R.id.editTextNote);
        TextView foodPriceView = (TextView) view.findViewById(R.id.foodPrice);

        final double product_size_s = getIntent().getDoubleExtra("product_size_s",0);
        final double product_size_l = getIntent().getDoubleExtra("product_size_l",0);
        final String foodPrice = getIntent().getStringExtra("price");

        foodPriceView.setText("Price: $" + foodPrice);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                    if (checkedId == R.id.radioButton) {
                        modelScale = (float)product_size_s;
                        foodSize = "S";
                    } else if (checkedId == R.id.radioButton2) {
                        modelScale = 1f;
                        foodSize = "M";
                    } else if (checkedId == R.id.radioButton3) {
                        modelScale = (float)product_size_l;
                        foodSize = "L";
                    }
                }
            });

        clearModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arFragment.getArSceneView().getScene().callOnHierarchy(node -> {
                    if (node instanceof AnchorNode) {
                        ((AnchorNode) node).getAnchor().detach();
                    }
                });
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int product_id = getIntent().getIntExtra("product_model",0);
                final String product_name = getIntent().getStringExtra("product_name");
                final String price = getIntent().getStringExtra("price");
                final int food_image = getIntent().getIntExtra("food_image",0);
                String product_name_size = product_name;
                String note = noteEdit.getText().toString();
                Double modelScaleDouble = (double)modelScale;

                if (foodSize == "S") {
                    product_name_size = product_name + " Small";
                } else if (foodSize == "M") {
                    product_name_size = product_name + " Medium";
                } else if (foodSize == "L") {
                    product_name_size = product_name + " Large";
                }

                add_to_shopping_cart(product_id, product_name_size, Double.parseDouble(price), food_image, modelScaleDouble, note);
            }
        });
    }

    public void showConfig(View v) {
        configWindow.show();
    }

    public void add_to_shopping_cart(int product_id, String product_name, double price, int image, double scale, String note){
        String productid_size = product_id + foodSize;

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        //Cursor cursor = cart.rawQuery("Select * from cart where product_id_size=?", new String [] {productid_size});

        /*if (cursor.getCount()>0) {
            cursor.moveToFirst();
            ContentValues cv = new ContentValues();
            cv.put("count", cursor.getInt(4) + 1);
            cart.update("cart", cv, "product_id_size = ?", new String [] {productid_size});
        } else {*/
        cart.execSQL("INSERT INTO cart (product_id, product_id_size, count, product_name, product_model, price, image, scale, note) VALUES(" + product_id + ", '" + productid_size + "', " + 1 + ", '" + product_name + "', '"+ product_model +"', " + price + ", " + image +" , "+ scale +" , '" + note + "');");

        //}

        Toast.makeText(getApplicationContext(), product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
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
