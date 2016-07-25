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
            MemoryUtils.Path.Builder pathBuilder = new MemoryUtils.Path.Builder(this);
            MemoryUtils.Path path = pathBuilder.directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                    .folder(CARPETA)
                    .file(ARCHIVO)
                    .build();
            MemoryUtils.Result<File> result = MemoryUtils.saveTextFile(texto, path, true);
            MessageUtil.alert(this, result.getMessage(), "OK");
            resetContent();
        }
    }

    public void cargar() {
        MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file("avisar")
                .build();
        MemoryUtils.Result<String> result = MemoryUtils.loadTextFile(path);
        if (result.isSuccessful()) {
            tvContenido.setText(result.getResult());
        } else {
            MessageUtil.alert(this, result.getMessage(), "Joder");
        }
    }

    public void eliminar() {
        MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file(ARCHIVO)
                .build();
        MemoryUtils.Result result = MemoryUtils.deleteFile(path);
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

            MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                    .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                    .folder(CARPETA)
                    .file(OBJETO)
                    .build();
            MemoryUtils.Result<File> result = MemoryUtils.saveObject(objeto, path);
            MessageUtil.alert(this, result.getMessage(), "OK");
            resetContent();
        }

        btnGuardar.setText("Guardar");
    }

    public void cargarObjeto() {
        btnCargar.setText("Objeto");

        MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file(OBJETO)
                .build();

        MemoryUtils.Result<MiObjeto> result = MemoryUtils.loadObject(path, MiObjeto.class);
        if (result.isSuccessful()) {
            tvContenido.setText(result.getResult().getPalabra());
        } else {
            MessageUtil.alert(this, result.getMessage(), "OK");
        }

        btnCargar.setText("Cargar");
    }

    public void eliminarObjeto() {
        btnEliminar.setText("Objeto");
        MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file(OBJETO)
                .build();
        MemoryUtils.Result result = MemoryUtils.deleteFile(path);
        MessageUtil.alert(this, result.getMessage(), "OK");
        btnEliminar.setText("Borrar");
        resetContent();
    }

    public void eliminarDuplicado(){
        MemoryUtils.Path path = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file("copia")
                .build();
        MemoryUtils.Result resultado = MemoryUtils.deleteFile(path);
        MessageUtil.alert(this, resultado.getMessage(), "OK");
    }

    public void copiarAsset(){
        MemoryUtils.Path destinationPath = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file("avisar")
                .build();

        MemoryUtils.Result<File> result = MemoryUtils.copyFromAssets(this, "avisar.txt",
                destinationPath);
        MessageUtil.alert(this, result.getMessage(), "OK");
    }

    public void duplicar(){
        MemoryUtils.Path originPath = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file(ARCHIVO)
                .build();

        MemoryUtils.Path destinationPath = new MemoryUtils.Path.Builder(this)
                .directory(MemoryUtils.Path.PUBLIC_EXTERNAL, null)
                .folder(CARPETA)
                .file("copia")
                .build();

        MemoryUtils.Result<File> resultado = MemoryUtils.duplicateFile(originPath, destinationPath);

        MessageUtil.alert(this, resultado.getMessage(), "OK");
    }

    private void resetContent() {
        etNuevoContenido.setText("");
        tvContenido.setText("");
    }
}
