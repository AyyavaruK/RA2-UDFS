/**
* The MinimumDistanceUDAFEvaluator program implements an application that
* simply displays nearest Latitude ,Longitude for give Latitude and Longitude.
*
* @author  Ayyavaru Reddy
* @version 1.0
* @since   05/03/2016
*/

package com.truckstop.ra2.hive;

import java.util.StringTokenizer;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;

public class DistanceCalculation extends UDAF {

  public static class MinimumDistanceUDAFEvaluator implements UDAFEvaluator {
	  
	  
	static final double NULL_VALUE = -9999.0;
	
	
    
    private Double result;
    private String outLat;
    private String outLon;
    private String inLat;
    private String inLon;
    
    
    public void init() {
      outLat = null;
      outLon = null;
      inLat = null;
      inLon = null;
      result = null;
    }

    public boolean iterate(String latLon) {
    	//System.out.println("Change in branch");
    	System.out.println("Change in Dev Branch");
    	System.out.println("Merging Concept:::");
    	System.out.println("::::::::::rebasetesting::::::");
    	
    	StringTokenizer token = new StringTokenizer(latLon,"$");
    	int i = 0;
    	Double orginalLat = null;
    	Double orginalLon = null;
    	Double callLat = null;
    	Double calLon = null;
    	
    	while(token.hasMoreTokens()){
    		if (i == 0){
    			orginalLat = Double.parseDouble(token.nextToken())/100;
    		}else if(i == 1){
    			orginalLon = Double.parseDouble(token.nextToken())/100;
    		}else if(i == 2){
    			callLat = Double.parseDouble(token.nextToken());
    		}else{
    			calLon = Double.parseDouble(token.nextToken());
    		}
    		i++;
    	}
    	
    	if (result == null){
    	
    		
    		result = distance(orginalLat, orginalLon, callLat, calLon);
			outLat = callLat+"";
	    	outLon = calLon+"";
	    	inLat = orginalLat+"";
	    	inLon = orginalLon+"";
    		
    	}else{
    		System.out.println("orginalLat::::"+orginalLat);
    		System.out.println("orginalLon::::"+orginalLon);
    		System.out.println("callLat::::"+callLat);
    		System.out.println("calLon::::"+calLon);
    		double distance = distance(orginalLat, orginalLon, callLat, calLon);
    		if (distance < result){
    			result = distance;
    			outLat = callLat+"";
    	    	outLon = calLon+"";
    	    	inLat = orginalLat+"";
    	    	inLon = orginalLon+"";
    			
    		}
    		
    		//result = Math.min(result, distance(orginalLat, orginalLon, callLat, calLon));
    	}
    	
      return true;
    }

    public String terminatePartial() {
      return inLat+"$"+inLon+"$"+outLat+"$"+outLon;
    }
    
    public boolean merge(String other){
    	return iterate(other);
    }
    public String terminate(){
    	System.out.println("outLat::::"+outLat);
    	System.out.println("outLon::::"+outLon);
    	return outLat+"$"+outLon;
    }
    
    
    /**
    * The distance method to find out distance between two latitudes and longitudes.
    */

    public Double distance(Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1 == null || lat1 <= -9999.0 || lon1 == null || lat1 < -9999.0 || lat2 == null || lat2 < -9999.0 || lon2 == null || lon2 < -9999.0) {
            return -9999.0;
        }
        double theta = lon1 - lon2;
        double dist = Math.sin(this.degreeToRadius(lat1)) * Math.sin(this.degreeToRadius(lat2)) + Math.cos(this.degreeToRadius(lat1)) * Math.cos(this.degreeToRadius(lat2)) * Math.cos(this.degreeToRadius(theta));
        dist = Math.acos(dist);
        dist = this.radiusToDegree(dist);
        dist = dist * 60.0 * 1.1515;
        return dist;
    }

    private double degreeToRadius(double deg) {
        return deg * 3.141592653589793 / 180.0;
    }

    private double radiusToDegree(double rad) {
        return rad * 180.0 / 3.141592653589793;
    }
    
    
    
    }
  

  
  
  
  }