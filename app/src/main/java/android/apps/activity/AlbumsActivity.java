package android.apps.activity;

import android.app.Activity;
import android.apps.R;
import android.apps.adapter.AlbumsAdapter;
import android.apps.bean.PhotoUpImageBucket;
import android.apps.utils.PhotoUpAlbumHelper;
import android.apps.utils.PhotoUpAlbumHelper.GetAlbumListInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.List;

public class AlbumsActivity extends Activity {

    private GridView gridView;
    private AlbumsAdapter adapter;
    private PhotoUpAlbumHelper photoUpAlbumHelper;
    private List<PhotoUpImageBucket> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.albums_gridview);
        init();
        loadData();
        onItemClick();
    }


    private void init() {
        gridView = (GridView) findViewById(R.id.album_gridv);
        adapter = new AlbumsAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void loadData() {
        photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
        photoUpAlbumHelper.init(this);
        photoUpAlbumHelper.setGetAlbumListInterface(new GetAlbumListInterface() {
            @Override
            public void getAlbumList(List<PhotoUpImageBucket> list) {

                adapter.setArrayList(list);
                adapter.notifyDataSetChanged();
                AlbumsActivity.this.list = list;
            }
        });
        photoUpAlbumHelper.execute(false);
    }


    private void onItemClick() {
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AlbumsActivity.this, AlbumItemActivity.class);
                intent.putExtra("imagelist", list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
