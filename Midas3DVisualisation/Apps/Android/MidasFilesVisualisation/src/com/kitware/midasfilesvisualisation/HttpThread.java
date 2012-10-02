package com.kitware.midasfilesvisualisation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class HttpThread implements Runnable {

	/*-----Attributes-----------------------------------------*/
	private String sUrl;
	private String response;

	/* -----Constructor-------------------------------------- */
	public HttpThread(String sUrl) {
		this.sUrl = sUrl;
	}

	/* ----- RUN --------------------------------------------- */
	public void run() {
		String str;
		StringBuffer buff = new StringBuffer();
		try {
			URL url = new URL(this.sUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			while ((str = in.readLine()) != null) {
				buff.append(str);
			}
		} catch (Exception e) {
			Log.e("HttpRequest", e.toString());
		}

		/*call display message activity using our response*/
		response = buff.toString();

	}

	/*----------Assessors-----------------------------------------*/
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
