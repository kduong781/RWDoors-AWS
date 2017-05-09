/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class imageBean implements Serializable {

	//private static final long serialVersionUID = 6081417964063918994L;
        private int imgId;
        private String name="",description =" test", file = " test";

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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

	public List<Image> getImages() throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

		Connection connect = null;

		String url = "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb";

		String username = "admin";
		String password = "mypassword";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, username, password);
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}

		List<Image> images = new ArrayList<Image>();
		PreparedStatement pstmt = connect
				.prepareStatement("select imgId, name, description, file from gallery");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			Image img = new Image();
			img.setImgId(rs.getInt("imgId"));
			img.setName(rs.getString("name"));
			img.setDescription(rs.getString("description"));
			img.setFile(rs.getBlob("file"));
                        img.setImgByte();
			images.add(img);

		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();

		return images;

	}
        
      /*  public void addImage() throws SQLException, ClassNotFoundException {
		Connection connect = null;

		String url = "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb";

		String username = "admin";
		String password = "mypassword";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, username, password);
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
                String sql = "INSERT INTO gallery(name,description) VALUES ('"+name + "','"+ description + "')";
                PreparedStatement stmt = connect.prepareStatement(sql);
  
               // stmt.setString(3, "test");
                stmt.executeUpdate(sql);
                connect.commit();
        }
*/
}