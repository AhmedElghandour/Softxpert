package com.softxpert.application.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

//    public static String onGitHubResponseError(Response response) {
//
//        if (response.code() == 403) {
//
//            Headers headers = response.headers();
//            Set<String> headerNames = headers.names();
//            long rateLimitReset = 0;
//
//            for (String headerName :
//                    headerNames) {
//                String headerValue = headers.get(headerName);
//                if (headerValue == null) {
//                    continue;
//                }
//                if (headerName.equals("X-RateLimit-Reset")) {
//                    rateLimitReset = Long.parseLong(headers.get(headerName));
//                    break;
//                }
//            }
//
//            if (rateLimitReset != 0) {
//                Date resetDate = new Date(rateLimitReset * 1000);
//                String resetTimeText = DateFormat.getTimeInstance().format(resetDate);
//                return "Uh Oh! it looks like you exceeded you API rate limit. Try again after " + resetTimeText;
//            }
//        }
//
//        return response.message();
//    }
    public static boolean isNetworkConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
