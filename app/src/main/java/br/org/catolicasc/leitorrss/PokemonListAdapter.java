package br.org.catolicasc.leitorrss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PokemonListAdapter extends ArrayAdapter {
    private static final String TAG = "PokemonListAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater; // Faz conversão layout resource em objeto
    private List<Pokemon> pokemon; //Lista que passa pro parametro la na aplicação.
    private Bitmap imagem;

    public PokemonListAdapter(Context context, int resource, List<Pokemon> aplicativos) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.pokemon = aplicativos;
    }

    @Override
    public int getCount() {
        return pokemon.size();
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int resposta = connection.getResponseCode();

                if (resposta != HttpURLConnection.HTTP_OK) { // se resposta não foi OK
                    if (resposta == HttpURLConnection.HTTP_MOVED_TEMP  // se for um redirect
                            || resposta == HttpURLConnection.HTTP_MOVED_PERM
                            || resposta == HttpURLConnection.HTTP_SEE_OTHER) {
                        // pegamos a nova URL e abrimos nova conexão!
                        String novaUrl = connection.getHeaderField("Location");
                        connection = (HttpURLConnection) new URL(novaUrl).openConnection();
                    }
                }
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Erro ao baixar imagem"
                        + e.getMessage());
            }

            return null;
        }
    }

    public void downloadImagem(String imgUrl) {
        ImageDownloader imageDownloader = new ImageDownloader();

        try {
            // baixar a imagem da internet
            imagem = imageDownloader.execute(imgUrl).get();
            // atribuir a imagem ao imageView
        } catch (Exception e) {
            Log.e(TAG, "downloadImagem: Impossível baixar imagem"
                    + e.getMessage());
        }
    }

    public Bitmap returnImagemTask(String imgUrl){
        ImageDownloader imageDownloader = new ImageDownloader();
        try {
            imagem = imageDownloader.execute(imgUrl).get();
            return imagem;
        } catch (Exception e) {
            Log.e(TAG, "downloadImagem: Impossível baixar imagem"
                    + e.getMessage());
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            Log.d(TAG, "getView: chamada com um convertView null");
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: recebeu um convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Pokemon appAtual = pokemon.get(position);

        viewHolder.txvID.setText(appAtual.getId());
        viewHolder.txvNome.setText(appAtual.getNome());
        downloadImagem(appAtual.getImgUrl());
        viewHolder.imgAppImg.setImageBitmap(imagem);


        return convertView;
    }

    private class ViewHolder {
        final TextView txvID;
        final TextView txvNome;
        final TextView txvAltura;
        final TextView txvPeso;
        final ImageView imgAppImg;

        ViewHolder(View v) {
            this.txvID = v.findViewById(R.id.tvID);
            this.txvNome = v.findViewById(R.id.tvNome);
            this.txvAltura = v.findViewById(R.id.tvAltura);
            this.txvPeso = v.findViewById(R.id.tvPeso);
            this.imgAppImg = v.findViewById(R.id.ivAppImg);
        }
    }
}
