package my.springbliss.grocery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sanitizer extends AppCompatActivity {
    private static final String apiurl="https://digitalcafe.us/springbliss/fetchadditem.php";
    ArrayList<SanitizerModel> freshFruitModels;
    RecyclerView recyclerView;
    Sanitizer_Adapter gridItemAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atta_and_other);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Fresh Fruits");

        recyclerView=findViewById(R.id.recycle_view_item);
        freshFruitModels=new ArrayList<>();

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        gridItemAdapter=new Sanitizer_Adapter(this,freshFruitModels);
        recyclerView.setAdapter(gridItemAdapter);
        progressDialog=new ProgressDialog(Sanitizer.this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Springbliss");
        progressDialog.setMessage("Please Wait......");

        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           // attaAndOtherModels.clear();
            try {
                //Toast.makeText(AttaAndOtherActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(response);
                String sucess = jsonObject.getString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (sucess.equals("1")) {
                    progressDialog.dismiss();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                       String category=object.getString("item_category");
                        //if (category.equals("Atta and other Flours")) {
                            String id = object.getString("id");
                            String name = object.getString("item_name");
                            String product_price = object.getString("item_mrp");
                            String selling = object.getString("item_outprice");
                            String weight = object.getString("item_weight");
                            String seller_id = object.getString("seller_id");
                            String item_descrip = object.getString("item_description");
                            String item_image = object.getString("item_image");
                            String u = "https://inventivepartner.com/Inventive_fruits/images/" + item_image;
                            freshFruitModels.add(new SanitizerModel(u, name, weight, product_price, selling, item_descrip, id, seller_id));
                            gridItemAdapter.notifyDataSetChanged();
                       // }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sanitizer.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

