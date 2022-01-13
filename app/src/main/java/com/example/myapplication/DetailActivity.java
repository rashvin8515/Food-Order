package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = new DBHelper(this);

        if(getIntent().getIntExtra("type",0)==1) {

            final int image = getIntent().getIntExtra("image", 0);
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String name = getIntent().getStringExtra("name");
            final String description = getIntent().getStringExtra("desc");



            binding.detailImages.setImageResource(image);
            binding.priceLabel.setText(String.format("%d", price));
            binding.foodName.setText(name);
            binding.detailDescription.setText(description);



            binding.insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isInserted = helper.insertOrder(
                            binding.nameBox.getText().toString(),
                            binding.phoneBox.getText().toString(),
                            price,
                            image,
                            description,
                            name,
                            Integer.parseInt(binding.quantity.getText().toString()));
                    if (isInserted) {
                        Toast.makeText(DetailActivity.this, "data success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            final int id = getIntent().getIntExtra("id",0);
            Cursor cursor = helper.getOrderById(id);
            final int image = cursor.getInt(4);
            binding.detailImages.setImageResource(image);
            binding.priceLabel.setText(String.format("%d", cursor.getInt(3)));
            binding.foodName.setText(cursor.getString(7));
            binding.detailDescription.setText(cursor.getString(7));

            binding.nameBox.setText(cursor.getString(1));
            binding.phoneBox.setText(cursor.getString(2));

            binding.insertButton.setText("Update Now");
            binding.insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated = helper.updateOrder(
                            binding.nameBox.getText().toString(),
                            binding.phoneBox.getText().toString(),
                            Integer.parseInt(binding.priceLabel.getText().toString()), image, binding.detailDescription.getText().toString(), binding.foodName.getText().toString(), 1, id);
                    if(isUpdated)
                        Toast.makeText(DetailActivity.this,"updated",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(DetailActivity.this,"failed",Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}