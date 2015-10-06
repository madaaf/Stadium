package com.excilys.stadium.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by excilys on 12/06/15.
 */
public class InternetUtil {
    Context context = null;
    public InternetUtil(Context context){
        this.context = context;
    }

    public boolean internet() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
