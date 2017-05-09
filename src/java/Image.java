
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;



public class Image {
    private int imgId;
    private String name, description, imgByte;
    Blob file;
   
    
    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setImgByte() throws SQLException, UnsupportedEncodingException  {
        if(file!= null) {
            int len = (int) file.length();
            byte[] byt = file.getBytes(1,len);
            //byte[] encodedBytes = Base64.getDecoder().decode(byt);
            byte[] encodedBytes = Base64.getEncoder().encode(byt);
            String base64DataString = new String(encodedBytes , "UTF-8");
            this.imgByte = "data:image/jpeg;base64, " + base64DataString;
        }

    }
    
    public String getImgByte() throws SQLException, UnsupportedEncodingException {
        return imgByte;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    public Image(int imgId, String name, String description, Blob file) {
        this.imgId = imgId;
        this.name = name;
        this.description = description;
        this.file = file;
    }

    public Image() {
        
    }


}
