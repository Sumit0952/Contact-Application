package com.example.contactapp
import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactapp.databinding.ActivityAddEditBinding
import com.example.contactapp.roomdb.DbBuilder
import com.example.contactapp.roomdb.entity.Contact
import com.github.dhaval2404.imagepicker.ImagePicker


class AddEditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddEditBinding.inflate(layoutInflater)
    }
    var contact = Contact()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

//                mProfileUri = fileUri
                binding.imageView3.setImageURI(fileUri)
                val imageBytes = contentResolver.openInputStream(fileUri)?.readBytes()
                contact.profile=imageBytes
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.imageView3.setOnLongClickListener() {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.image_dialog)
            var image = dialog.findViewById<ImageView>(R.id.imageView)
            var imageObject = binding.imageView3.drawable
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);

            dialog.getWindow()?.setLayout(80, 80);
            val lp = WindowManager.LayoutParams()
            lp.blurBehindRadius = 50
            lp.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
          lp.width = WindowManager.LayoutParams.WRAP_CONTENT
          lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            //dialog.getWindow()?.setLayout(80, 80);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            image.setImageDrawable(imageObject)
            dialog.window?.attributes = lp
            dialog.show()
            true
        }
        binding.savaContact.setOnClickListener{
            contact.name = binding.name.editText?.text.toString()
            contact.phoneNumber = binding.phoneNumber.text.toString()
            contact.email = binding.email.text.toString()

            var db = DbBuilder.getdb(this)
            val i = db?.ContactDao()?.createContact(contact)

            if (i != null) {
                if(i>0){
                    Toast.makeText(this@AddEditActivity,i.toString(),Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.imageView3.setOnClickListener{
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        findViewById<ImageView>(R.id.imageView)

    }
}