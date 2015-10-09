package main.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import main.R;

/**
 * Created by thomas on 09/10/15.
 */
public class ViewHolder {
    private ImageView image;
    private TextView name;
    private TextView alias;
    private TextView text;
    private Button button;

    public ViewHolder(View view){
        image = (ImageView) view.findViewById(R.id.imageAvi);
        name = (TextView) view.findViewById(R.id.username);
        alias = (TextView) view.findViewById(R.id.login);
        text = (TextView) view.findViewById(R.id.textTweet);
        button = (Button) view.findViewById(R.id.buttonRT);

    }


    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getAlias() {
        return alias;
    }

    public void setAlias(TextView alias) {
        this.alias = alias;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
