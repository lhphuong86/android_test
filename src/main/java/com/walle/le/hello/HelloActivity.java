package com.walle.le.hello;

import android.app.Activity;
import android.os.AsyncTask;

import android.os.Bundle;
import android.widget.TextView;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.joda.time.LocalTime;
import com.walle.le.model.Greating;
public class HelloActivity extends Activity {

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);
    }

    @Override
    public void onStart() {
        super.onStart();
		new HttpRequestTask().execute();
        LocalTime currentTime = new LocalTime();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("The current local time is: " + currentTime);
    }
	
	
	private class HttpRequestTask extends AsyncTask<Void, Void, Greating> {
        @Override
        protected Greating doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greating greating = restTemplate.getForObject(url, Greating.class);
                return greating;
            } catch (Exception e) {
                //Log.e("HelloActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greating greating) {
			        TextView textView = (TextView) findViewById(R.id.text_view);
					textView.setText(greating.getContent());
           
        }

    }

}