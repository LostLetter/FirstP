package com.tt.oprateLib.base;

import java.security.cert.X509Certificate;

// if not extends ControlSdkBaseActivity , implements this interface to get result from IBank

public interface SdkOprateInterface {
	public abstract void handleCertData(int errorCode, String result, X509Certificate x509Cert);

	public abstract void handleSignResult(int errorCode, String result);
}
