package com.example.textconverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class Retake extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUEST_CODE = 1001;

    EditText recogtext;
    TextRecognizer textRecognizer;
    Button retakebtn, copybtn;
    Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retake);


        recogtext = findViewById(R.id.recogtext);
        retakebtn = findViewById(R.id.retakebtn);
        copybtn = findViewById(R.id.copybtn);
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);


        String imageUriString = getIntent().getStringExtra("imageUri");

        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            recognizetxt(imageUri);
        } else {
            Toast.makeText(this, "No image URI passed", Toast.LENGTH_SHORT).show();
        }


        retakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retakeImage();
            }
        });


        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextToClipboard();
            }
        });
    }

    private void recognizetxt(Uri imageUri) {
        try {

            InputImage inputImage = InputImage.fromFilePath(Retake.this, imageUri);


            textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {

                            String recognizedText = text.getText();
                            recogtext.setText(recognizedText);  // Display recognized text
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {

                            Toast.makeText(Retake.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }


    private void retakeImage() {
        ImagePicker.with(Retake.this)
                .crop()                // Enables cropping (Optional)
                .compress(1024)        // Compresses image to be less than 1MB (Optional)
                .maxResultSize(1080, 1080)  // Sets max resolution to 1080x1080 (Optional)
                .start(IMAGE_PICKER_REQUEST_CODE); // Start the image picker
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Image is retaken", Toast.LENGTH_SHORT).show();
            imageUri = data.getData();  // Get the new image URI
            recognizetxt(imageUri);     // Process the new image for text recognition
        } else {
            Toast.makeText(this, "Image retake canceled", Toast.LENGTH_SHORT).show();
        }
    }


    private void copyTextToClipboard() {
        String textToCopy = recogtext.getText().toString();

        if (!textToCopy.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Recognized Text", textToCopy);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No text to copy", Toast.LENGTH_SHORT).show();
        }
    }
}
