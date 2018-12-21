package test;

public class MyDaireAlanHesap {
	    public double yariCap;
	    public double dairesay;
	    public double alan()
	    {
	    	if (dairesay < 5) 
	    	{
	    		return 3 * yariCap * yariCap * dairesay;
	    	} else
	    	{
	    		return 3 * yariCap * yariCap;
	    	} 
	    }
	}