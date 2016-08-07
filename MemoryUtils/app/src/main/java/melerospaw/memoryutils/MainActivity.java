package melerospaw.memoryutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import melerospaw.memoryutil.MemoryUtils;
import melerospaw.memoryutil.Path;
import melerospaw.memoryutil.Result;


public class MainActivity extends AppCompatActivity {

    private final String OBJETO = "miObjetito";
    private final String CARPETA = "resguardo";
    private final String ARCHIVO = "miArchivito.txt";

    @Bind(R.id.et_nuevo_contenido)
    EditText etNuevoContenido;
    @Bind(R.id.tv_contenido)
    TextView tvContenido;
    @Bind(R.id.btn_guardar)
    Button btnGuardar;
    @Bind(R.id.btn_cargar)
    Button btnCargar;
    @Bind(R.id.btn_eliminar)
    Button btnEliminar;
    @Bind(R.id.btn_borrar_duplicado)
    Button btnBorrarDuplicado;
    @Bind(R.id.btn_copiar_assets)
    Button btnCopiarAssets;
    @Bind(R.id.btn_duplicar)
    Button btnDuplicar;

//    @Bind(R.id.scroll)  ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_layout);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_guardar, R.id.btn_cargar, R.id.btn_eliminar, R.id.btn_borrar_duplicado,
            R.id.btn_copiar_assets, R.id.btn_duplicar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guardar:
                guardar();
                break;
            case R.id.btn_cargar:
                cargar();
                break;
            case R.id.btn_eliminar:
                eliminar();
                break;
            case R.id.btn_borrar_duplicado:
                eliminarDuplicado();
                break;
            case R.id.btn_copiar_assets:
                copiarAsset();
                break;
            case R.id.btn_duplicar:
                duplicar();
                break;
        }
    }

    @OnLongClick({R.id.btn_guardar, R.id.btn_cargar, R.id.btn_eliminar})
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guardar:
                guardarObjeto();
                break;
            case R.id.btn_cargar:
                cargarObjeto();
                break;
            case R.id.btn_eliminar:
                eliminarObjeto();
                break;
        }

        return true;
    }

    public void guardar() {
        String texto = etNuevoContenido.getText().toString();
        if (estaElTextoEscrito(texto)) {
            Path.Builder pathBuilder = new Path.Builder(this);
            Path path = pathBuilder.storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                    .folder(CARPETA)
                    .file(ARCHIVO)
                    .build();
            Result<File> result = MemoryUtils.saveTextFile(texto, path, true);
            MessageUtil.alert(this, result.getMessage(), "OK");
            resetContent();
        }
    }

    public void cargar() {
        Path path = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file("avisar")
                .build();
        Result<String> result = MemoryUtils.loadTextFile(path);
        if (result.isSuccessful()) {
            tvContenido.setText(result.getResult());
        } else {
            MessageUtil.alert(this, result.getMessage(), "Joder");
        }
    }

    public void eliminar() {
        Path path = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
//                .file(ARCHIVO)
                .build();
        Result result = MemoryUtils.deleteFile(path, true);
        MessageUtil.alert(this, result.getMessage(), "OK");
        resetContent();
    }

    public boolean estaElTextoEscrito(String contenido) {
        if (TextUtils.isEmpty(contenido)) {
            Toast.makeText(this, "No has escrito nada.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void guardarObjeto() {
        btnGuardar.setText("Objeto");
        String contenido = etNuevoContenido.getText().toString();
        if (estaElTextoEscrito(contenido)) {

            MiObjeto objeto = new MiObjeto();
            objeto.setPalabra(contenido);

            Path path = new Path.Builder(this)
                    .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                    .folder(CARPETA)
                    .file(OBJETO)
                    .build();
            Result<File> result = MemoryUtils.saveObject(objeto, path);
            MessageUtil.alert(this, result.getMessage(), "OK");
            resetContent();
        }

        btnGuardar.setText("Guardar");
    }

    public void cargarObjeto() {
        btnCargar.setText("Objeto");

        Path path = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file(OBJETO)
                .build();

        Result<MiObjeto> result = MemoryUtils.loadObject(path, MiObjeto.class);
        if (result.isSuccessful()) {
            tvContenido.setText(result.getResult().getPalabra());
        } else {
            MessageUtil.alert(this, result.getMessage(), "OK");
        }

        btnCargar.setText("Cargar");
    }

    public void eliminarObjeto() {
        btnEliminar.setText("Objeto");
        Path path = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file(OBJETO)
                .build();
        Result result = MemoryUtils.deleteFile(path, true);
        MessageUtil.alert(this, result.getMessage(), "OK");
        btnEliminar.setText("Borrar");
        resetContent();
    }

    public void eliminarDuplicado(){
        Path path = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file("copia")
                .build();
        Result resultado = MemoryUtils.deleteFile(path, true);
        MessageUtil.alert(this, resultado.getMessage(), "OK");
    }

    public void copiarAsset(){
        Path destinationPath = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file("avisar")
                .build();

        Result<File> result = MemoryUtils.copyFromAssets(this, "avisar.txt",
                destinationPath);
        MessageUtil.alert(this, result.getMessage(), "OK");
    }

    public void duplicar(){
        Path originPath = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file(ARCHIVO)
                .build();

        Path destinationPath = new Path.Builder(this)
                .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
                .folder(CARPETA)
                .file("copia")
                .build();

        Result<File> resultado = MemoryUtils.duplicateFile(originPath, destinationPath);

        MessageUtil.alert(this, resultado.getMessage(), "OK");
    }

    private void resetContent() {
        etNuevoContenido.setText("");
        tvContenido.setText("");
    }
}
