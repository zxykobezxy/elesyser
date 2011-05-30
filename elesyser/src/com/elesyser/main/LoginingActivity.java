package com.elesyser.main;

import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.LoginHelper;
import com.elesyser.util.UserInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class LoginingActivity extends Activity {
	
	String __VIEWSTATE = "/wEPDwUKLTQxMTY3OTc5Mg9kFgYCAw9kFgJmDw8WAh4EVGV4dAUj5b2T5YmN5Zyo57q/55So5oi35pyJ77yaPGI+MTU5ODwvYj5kZAIFD2QWAgIBDxYCHgtfIUl0ZW1Db3VudAIHFg5mD2QWAmYPFQQDNTI1QTIwMTHlubQ25pyI5YWo5Zu95aSn5a2m6Iux6K+t5Zub5YWt57qn6ICD6K+V5a+55q+U6ICD5oql5ZCN6YCa55+lQTIwMTHlubQ25pyI5YWo5Zu95aSn5a2m6Iux6K+t5Zub5YWt57qn6ICD6K+V5a+55q+U6ICD5oql5ZCN6YCa55+lCjIwMTEtMDUtMzBkAgEPZBYCZg8VBAM1MjRI5YWz5LqOMjAxMO+8jTIwMTHlrablubTnrKwy5a2m5pyf5pyf5pyr6ICD6K+V5a6J5o6S572R5LiK5p+l6K+i55qE6YCa55+lSOWFs+S6jjIwMTDvvI0yMDEx5a2m5bm056ysMuWtpuacn+acn+acq+iAg+ivleWuieaOkue9keS4iuafpeivoueahOmAmuefpQoyMDExLTA1LTI2ZAICD2QWAmYPFQQDNTIzSg0K6YCJ5L+u44CK5Zyo5a6e6aqM5Lit5o6i56m25YyW5a2m44CL6K++56iL5pqR5pyf54+t55qE5ZCM5a2m6K+35rOo5oSP77yaSg0K6YCJ5L+u44CK5Zyo5a6e6aqM5Lit5o6i56m25YyW5a2m44CL6K++56iL5pqR5pyf54+t55qE5ZCM5a2m6K+35rOo5oSP77yaCjIwMTEtMDUtMjVkAgMPZBYCZg8VBAM1MjIr5YWz5LqOMjAxMS0yMDEy5a2m5bm056ysMeWtpuacn+mAieivvumAmuefpSvlhbPkuo4yMDExLTIwMTLlrablubTnrKwx5a2m5pyf6YCJ6K++6YCa55+lCjIwMTEtMDUtMjVkAgQPZBYCZg8VBAM1MjE9MjAxMeW5tOS4iua1t+W4gumrmOetieWtpuagoeiuoeeul+acuuetiee6p+iAg+ivleaKpeWQjemAmuefpT0yMDEx5bm05LiK5rW35biC6auY562J5a2m5qCh6K6h566X5py6562J57qn6ICD6K+V5oql5ZCN6YCa55+lCjIwMTEtMDUtMDVkAgUPZBYCZg8VBAM1MjBBMjAxMC0yMDEx5a2m5bm056ysMuWtpuacn+mHjeS/ruWPiuS6jOS4k+ivvueoi+e8tOi0uSDlu7bmnJ/pgJrnn6VBMjAxMC0yMDEx5a2m5bm056ysMuWtpuacn+mHjeS/ruWPiuS6jOS4k+ivvueoi+e8tOi0uSDlu7bmnJ/pgJrnn6UKMjAxMS0wNC0yN2QCBg9kFgJmDxUEAzUxOUMyMDEx5bm0NeaciDI45pel5YWo5Zu95aSn5a2m6Iux6K+t5Zub57qn44CB5YWt57qn572R6ICD5oql5ZCN6YCa55+lQzIwMTHlubQ15pyIMjjml6Xlhajlm73lpKflraboi7Hor63lm5vnuqfjgIHlha3nuqfnvZHogIPmiqXlkI3pgJrnn6UKMjAxMS0wNC0yNmQCBw88KwALAGRkZOTHCrQf0X8zWAY/E/WpXszeSF0=";
    String __EVENTVALIDATION = "/wEWBwLhgf21AwKl1bKzCQKd+7qdDgLI6POXAQLX6POXAQLYh9n5DQKM54rGBkFeDGRo40KEEPi0yTNc1yEJcggX";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logining);
        
        new CheckUser().execute(UserInfo.Username, UserInfo.Password);
    }
    
    private class CheckUser extends AsyncTask<String,Integer,Integer>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(Integer result){
			
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO check the userinfo via the Internet
			LoginHelper login = new LoginHelper();
			boolean ret = login.Valid(params[0], params[1]);
			return null;
		}
    }
}
