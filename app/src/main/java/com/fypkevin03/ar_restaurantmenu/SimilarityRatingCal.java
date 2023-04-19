package com.fypkevin03.ar_restaurantmenu;

import java.util.Map;

public abstract class SimilarityRatingCal {
    public String userId;
    public int similarityNum;


    public SimilarityRatingCal(String id, int neighborSize) {
        userId = id;
        similarityNum = neighborSize;
    }

    private double calUserSim(Map data, Integer userID) {
        double similarityScore = 0.0;
        double nomUser = 0.0;
        double nomOther = 0.0;
        int minNumCommon = 0;

//        ArrayList<String> userMovies = for(String user: data.keySet())
//        ArrayList<String> userMovies = user.getMoviesRated();
//        {
//
//        }
//        double userAvg = user.getAvgRating();
//        ArrayList<String> otherMovies = other.getMoviesRated();
//        double otherAvg = other.getAvgRating();
//        for (String mid1: userMovies) {
//            for (String mid2 : otherMovies) {
//                if (mid1.equals(mid2)) {
////                    minNumCommon++;
//                    double userScore = user.getRating(mid1) - userAvg;
//                    double otherScore = other.getRating(mid2) - otherAvg;
//                    similarityScore += (userScore) * (otherScore);
//                    nomUser += Math.pow(userScore, 2);
//                    nomOther += Math.pow(otherScore, 2);
//                }
//            }
        return similarityScore;
    }

}

