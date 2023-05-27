package ru.lairon.laberis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.lairon.laberis.moder.Product;

public class ProductItemAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;
    private LayoutInflater layoutInflater;

    public ProductItemAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.activity_product_item_list, null);
        Product product = products.get(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        TextView price = convertView.findViewById(R.id.price);
        ImageView image = convertView.findViewById(R.id.image);

        title.setText(product.getType().getRussianName() + " | " + product.getTitle());
        description.setText(product.getDescription());
        price.setText(product.getPrice() + " ₽");
        image.setImageBitmap(product.getImage());

        return convertView;
    }
}
