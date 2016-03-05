package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment
public class WallpaperFragment extends Fragment{

    String Tag = "WallpaperFragment";
    ImageLoaderConfiguration _config;

    @ViewById(R.id.cancel_imageButton)
    ImageButton _cancelImageButton;
    @ViewById(R.id.left_imageButton)
    ImageButton _leftImageButton;
    @ViewById(R.id.right_imageButton)
    ImageButton _rightImageButton;
    @ViewById(R.id.show_selected_photo_imageView)
    ImageView _selectedPhotoImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallpaper_fragment, container, false);
    }

    @AfterViews
    public void setupViews(){
        /********************만약 사이즈 조절이 필요하다면***************
        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
        resizeOptions.inSampleSize = 10;
        **********************************************************/
        _config = new ImageLoaderConfiguration.Builder(getActivity())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(_config);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.colorAccent) // 로딩중 이미지 설정
                .showImageForEmptyUri(R.color.colorPrimary) // Uri주소가 잘못되었을경우(이미지없을때)
                .showImageOnFail(R.color.colorPrimaryDark) // 로딩 실패시
               // .decodingOptions(resizeOptions)
                .resetViewBeforeLoading(false)  // 로딩전에 뷰를 리셋하는건데 false로 하세요 과부하!
                .cacheInMemory(false) // 메모리케시 사용여부
                .considerExifParams(false) // 사진이미지의 회전률 고려할건지
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 스케일타입설정
                .bitmapConfig(Bitmap.Config.RGB_565) // 이미지 컬러방식
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(_selectedPhotoImageView,false); //ImageView속성을 따르기 위해서
        imageLoader.displayImage("drawable://"+R.drawable.test_wallpaper ,imageAware, options);
    }
    public void setLodingImageView(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.colorAccent) // 로딩중 이미지 설정
                .showImageForEmptyUri(R.color.colorPrimary) // Uri주소가 잘못되었을경우(이미지없을때)
                .showImageOnFail(R.color.colorPrimaryDark) // 로딩 실패시
                // .decodingOptions(resizeOptions)
                .resetViewBeforeLoading(false)  // 로딩전에 뷰를 리셋하는건데 false로 하세요 과부하!
                .cacheInMemory(false) // 메모리케시 사용여부
                .considerExifParams(false) // 사진이미지의 회전률 고려할건지
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 스케일타입설정
                .bitmapConfig(Bitmap.Config.ARGB_8888) // 이미지 컬러방식
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(_selectedPhotoImageView,true); //ImageView속성을 따르기 위해서
        imageLoader.displayImage("drawable://"+R.drawable.copylodingimage ,imageAware, options);
    }

    @UiThread
    public void setImageView(String imagePath){
        /********************만약 사이즈 조절이 필요하다면****************
         BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
         resizeOptions.inSampleSize = 10;
         **********************************************************/
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.colorAccent) // 로딩중 이미지 설정
                .showImageForEmptyUri(R.color.colorPrimary) // Uri주소가 잘못되었을경우(이미지없을때)
                .showImageOnFail(R.color.colorPrimaryDark) // 로딩 실패시
               // .decodingOptions(resizeOptions)
                .resetViewBeforeLoading(false)  // 로딩전에 뷰를 리셋하는건데 false로 하세요 과부하!
                .cacheInMemory(false) // 메모리케시 사용여부
                .considerExifParams(false) // 사진이미지의 회전률 고려할건지
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 스케일타입설정
                .bitmapConfig(Bitmap.Config.ARGB_8888) // 이미지 컬러방식
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(_selectedPhotoImageView,true); //ImageView속성을 따르기 위해서
        imageLoader.displayImage("file://"+imagePath , imageAware , options);
    }

    public Uri resourceToUri (int resID) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(resID)
                + '/' + getResources().getResourceTypeName(resID) + '/' +
                getResources().getResourceEntryName(resID));
        return  imageUri;
    }
    @Click(R.id.cancel_imageButton)
    void leftViewClicked() { Log.d(Tag,"1"); }
    @Click(R.id.right_imageButton)
    void cancelClicked(){
        Log.d(Tag,"2");
    }
    @Click(R.id.left_imageButton)
    void rightViewClicked(){
        Log.d(Tag,"3");
    }
}
