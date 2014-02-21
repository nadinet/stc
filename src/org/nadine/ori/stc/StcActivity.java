package org.nadine.ori.stc;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.widget.ToggleButton;

public class StcActivity extends IOIOActivity implements CvCameraViewListener2 {

	private static final String TAG = "StcActivity";

	private ToggleButton button_;
	private Detector detector_;
	private Stc stc_;
	private int width_, height_;
	private CameraBridgeViewBase cameraView_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stc);
		button_ = (ToggleButton) findViewById(R.id.ioio_btn);
		cameraView_ = (CameraBridgeViewBase) findViewById(R.id.camera_surface_view);
		cameraView_.setVisibility(SurfaceView.VISIBLE);
		cameraView_.setCvCameraViewListener(this);
		cameraView_.setCameraIndex(1); // camera id 0 is back and camera id 1 is front
		stc_ = new Stc();
		detector_ = Detector.getInstance();

	}

	/*
	 * enable opencv library
	 */
	private BaseLoaderCallback openCvLoader_ = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");
				cameraView_.enableView();
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (cameraView_ != null){
			cameraView_.disableView();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (cameraView_ != null){
			cameraView_.disableView();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, openCvLoader_);

	}

	/*
	 * enable ioio looper
	 */
	class Looper extends BaseIOIOLooper {
		/** The on-board LED. */
		private DigitalOutput led_;

		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			led_ = ioio_.openDigitalOutput(IOIO.LED_PIN, true);
		}

		/**
		 * Called repetitively while the IOIO is connected.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
		 */
		@Override
		public void loop() throws ConnectionLostException {
			led_.write(!button_.isChecked());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * A method to create our IOIO thread.
	 * 
	 * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
	 */
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stc, menu);
		return true;
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		 width_ = width;
		 height_ = height;
		 Log.i(TAG,"onCameraViewStarted done.");
	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub

	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		Mat rgbaImage = inputFrame.rgba();
		Mat outputImage = detector_.process(rgbaImage);
		return outputImage;
	}

}
