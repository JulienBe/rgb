package hacknslash.rgb.general.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.io.Serializable;

public final class BloomShaderLoader implements Serializable {

  static final public ShaderProgram createShader(String vertexName,
                                                 String fragmentName) {

    String vertexShader = Gdx.files.internal(
            vertexName
                    + ".vertex.glsl").readString();
    String fragmentShader = Gdx.files.internal(
            fragmentName
                    + ".fragment.glsl").readString();
    ShaderProgram.pedantic = false;
    ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
    if (!shader.isCompiled()) {
      System.out.println(shader.getLog());
      Gdx.app.exit();
    }
    return shader;
  }
}

