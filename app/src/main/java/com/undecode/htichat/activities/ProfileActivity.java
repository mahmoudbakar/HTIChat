package com.undecode.htichat.activities;


import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.undecode.htichat.R;
import com.undecode.htichat.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.imageCover)
    ImageView imageCover;
    @BindView(R.id.imageProfile)
    CircleImageView imageProfile;
    @BindView(R.id.btnEditImage)
    ImageButton btnEditImage;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.edPhone)
    EditText edPhone;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.rdMale)
    MaterialRadioButton rdMale;
    @BindView(R.id.rdFemale)
    MaterialRadioButton rdFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.txtBirthDate)
    TextView txtBirthDate;
    @BindView(R.id.btnEditDate)
    ImageButton btnEditDate;
    @BindView(R.id.txtPassword)
    TextView txtPassword;
    @BindView(R.id.btnEditPassword)
    ImageButton btnEditPassword;
    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @Override
    protected int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {
        showBackArrow();
    }

    @OnClick(R.id.btnEditImage)
    public void onBtnEditImageClicked() {
//        final File[] actualImage = new File[1];
//        final boolean[] isImageChanged = {false};
//
//        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
//            @Override
//            public void onPickResult(PickResult r) {
//                try {
//                    actualImage[0] = FileUtils.from(ProfileActivity.this, r.getUri());
//                    isImageChanged[0] = true;
//                    Glide.with(imageProfile).clear(imageProfile);
//                    imageProfile.setImageBitmap(r.getBitmap());
//                    Glide.with(imageCover).clear(imageCover);
//                    imageCover.setImageBitmap(r.getBitmap());
//                    //Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        })
//                .setOnPickCancel(new IPickCancel() {
//                    @Override
//                    public void onCancelClick() {
//                    }
//                }).show((FragmentActivity) this);
    }

    @OnClick(R.id.btnEditDate)
    public void onBtnEditDateClicked() {
        Calendar mcurrentTime = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int mYear = newDate.get(Calendar.YEAR);
                int mMonth = newDate.get(Calendar.MONTH) + 1;
                int mDay = newDate.get(Calendar.DAY_OF_MONTH);
                txtBirthDate.setText(mYear + "-" + mMonth + "-" + mDay);
            }
        }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    @OnClick(R.id.btnEditPassword)
    public void onBtnEditPasswordClicked() {

    }

    @OnClick(R.id.fabSave)
    public void onFabSaveClicked() {
        showToast("Changes Saved.");
        finish();
    }
}
