package shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class ShaderProgram {

   private int programID;
   private int vertexShaderID;
   private int framentShaderID;

   public ShaderProgram(String vertexFile, String fragmentFile) {
       vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
       framentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
       programID = GL20.glCreateProgram();
       GL20.glAttachShader(programID, vertexShaderID);
       GL20.glAttachShader(programID, framentShaderID);
       bindAttributes();
       GL20.glLinkProgram(programID);
       GL20.glValidateProgram(programID);
   }

   public void start(){
       GL20.glUseProgram(programID);
   }

   public void stop(){
       GL20.glUseProgram(0);
   }

   public void cleanUP(){
       stop();
       GL20.glDetachShader(programID, vertexShaderID);
       GL20.glDetachShader(programID, framentShaderID);
       GL20.glDeleteShader(vertexShaderID);
       GL20.glDeleteShader(framentShaderID);
       GL20.glDeleteProgram(programID);
   }

   protected abstract void bindAttributes();

   protected void bindAttribute(int attribute, String variableName) {
       GL20.glBindAttribLocation(programID, attribute, variableName);
   }

    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }


}
