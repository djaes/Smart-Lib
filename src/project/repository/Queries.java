package project.repository;

import project.service.Artwork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 02/06/2017.
 */
public class Queries {
    public static final String DELETE_CAT_ART = "DELETE FROM `category_has_artwork` WHERE `artwork_id` = %d";
    public static final String DELETE_ART = "DELETE FROM `artwork` WHERE `id` = %d";
    public static final String INSERT_INTO_ART = "INSERT INTO `artwork` (`id`, `title`, `rating`, `comment`) VALUES (NULL, '%t', %r, '%c')";
    private ConnectDB myConnect = new ConnectDB();
    private Connection connection = myConnect.getConnection();



    private void requestInsertUpdateDeleteInDB(String request) {
        Statement st;
        try {
            st = connection.createStatement();
            st.executeUpdate(request);
        } catch (SQLException e) {
            System.out.println("Problem Request");
            e.printStackTrace();
        }
    }

    private ResultSet requestSelectOnDB(String request) {
        Statement st = null;
        try {
            st = connection.createStatement();
            return st.executeQuery(request);
        } catch (SQLException e) {
            System.out.println("Problem Select Request");
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList<String> getListCharat(String charact) throws SQLException {
        ObservableList<String>  l = FXCollections.observableArrayList();
        String r = "SELECT value FROM "+ charact +";";
        ResultSet result = requestSelectOnDB(r);
        while (result.next ( )) {
            l.add( result.getString ( "value" ) );
        }
        return l;
    }


    public String selectDataByRequest(String request, String nameColumn) {
        try {
            ResultSet result = requestSelectOnDB(request);
            result.first();
            return result.getString(nameColumn);
        } catch (SQLException e) {
            System.out.println("probleme select data ");
            e.printStackTrace();
        }
        return null;
    }


    public void updateArtwork(int idArtwork, String charactName, int idCharact){
        String request = "UPDATE `category_has_artwork` SET `"+ charactName+"_id` = '"+idCharact+"'" +
                "WHERE  `category_has_artwork`.`artwork_id` = "+ idArtwork;
        requestInsertUpdateDeleteInDB( request );
    }

    public List<List<String >> searchCharactByTitle ( String searchTitle ) throws SQLException {
        List<List<String >> superList = new ArrayList<List<String>>();
        superList.add(new ArrayList<String >());
        String r = "SELECT * FROM `médiathèque` WHERE `title` LIKE '" + searchTitle + "'";
        ResultSet result = requestSelectOnDB ( r );
        int c =0 ;
        while (result.next ( )) {
            superList.get(c).add ( result.getString ( "title" ) );
            superList.get(c).add ( result.getString ( "rating" ) );
            superList.get(c).add ( result.getString ( "comment" ) );
            superList.get(c).add ( result.getString ( "genre" ) );
            superList.get(c).add ( result.getString ( "origin" ) );
            superList.get(c).add ( result.getString ( "category" ) );
            superList.get(c).add ( result.getString ( "support" ) );
            superList.get(c).add ( result.getString ( "creator" ) );
            superList.get(c).add ( result.getString ( "year" ) );
            c++;
            superList.add(new ArrayList<String>());

        }
        return superList;
    }

    public ObservableList<Artwork> getMediateque(){
        ObservableList<Artwork> o = FXCollections.observableArrayList();
        String r = "SELECT * FROM `médiathèque`";
        ResultSet rs = requestSelectOnDB(r);
        try {
            while (rs.next()){
                Artwork a = new Artwork();
                a.setTitle(rs.getString("title"));
                a.setComment(rs.getString("comment"));
                a.setYear(rs.getInt("year"));
                a.setCategory(rs.getString("category"));
                a.setCreator(rs.getString("creator"));
                a.setGenre(rs.getString("genre"));
                a.setId(rs.getInt("id"));
                a.setOrigin(rs.getString("origin"));
                a.setRating(rs.getInt("rating"));
                a.setSupport(rs.getString("support"));

                o.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Probleme de connection a la DB");
            //e.printStackTrace();
        }
        return o;
    }

    public boolean deleteArtwork(int id) {
        String r = String.format(DELETE_CAT_ART, id);
        requestInsertUpdateDeleteInDB(r);
        r = String.format(DELETE_ART, id);
        requestInsertUpdateDeleteInDB(r);
        return true;
    }


    public void createCharact(String charact, String charactValue) throws SQLException {
        //Pour Charact
        PreparedStatement st = connection.prepareStatement("SELECT value FROM " + charact + ";");
        ResultSet rs = st.executeQuery();
        List<String> arrayResult = new ArrayList<>();
        while (rs.next()) {
            arrayResult.add(rs.getString("value"));
        }
        if (!arrayResult.contains(charactValue)) {
            String catR = "INSERT INTO "+charact+" (id, value) VALUES (NULL, '"+charactValue+"');";
            requestInsertUpdateDeleteInDB(catR);
        }
    }

    public void createYear(Integer yearValue) throws SQLException {
        //Pour year
        PreparedStatement st = connection.prepareStatement("SELECT value FROM year ;");
        ResultSet rs = st.executeQuery();
        List<String> arrayResult = new ArrayList<>();
        while (rs.next()) {
            arrayResult.add(rs.getString("value"));
        }
        if (!arrayResult.contains(yearValue)) {
            String catR = "INSERT INTO year (id, value) VALUES (NULL, '"+yearValue+"');";
            requestInsertUpdateDeleteInDB(catR);
        }
    }

    public void createUpdateArtwork(String create_update, Artwork artwork) throws SQLException {
        createCharact("category",artwork.getCategory());
        int idCat = selectId("category", artwork.getCategory());
        createCharact("genre",artwork.getGenre());
        int idGenre = selectId("genre", artwork.getGenre());
        createCharact("creator",artwork.getCreator());
        int idCreator = selectId("creator", artwork.getCreator());
        createCharact("origin",artwork.getOrigin());
        int idOrigin = selectId("origin", artwork.getOrigin());
        createCharact("support",artwork.getSupport());
        int idSupport = selectId("support", artwork.getSupport());
        createYear(artwork.getYear());
        int idYear = selectIdYear(artwork.getYear());
        if ("create".equals(create_update)) {
            String r = "INSERT INTO artwork (id, title, rating, comment) VALUES (NULL, '" + artwork.getTitle() + "', " +
                    artwork.getRating() + ", '" + artwork.getComment() + "')";
            requestInsertUpdateDeleteInDB(r);
            int idArtwork = selectId("artwork", artwork.getTitle());
            r = "INSERT INTO category_has_artwork (category_id, artwork_id, support_id, year_id, origin_id, creator_id," +
                    " genre_id) VALUES ('" + idCat + "', '" + idArtwork + "', '" + idSupport + "', '" + idYear + "', '" +
                    idOrigin + "', '" + idCreator + "', '" + idGenre + "');";
            requestInsertUpdateDeleteInDB(r);
        } else if ("update".equals(create_update)){
            int idArtwork = selectId("artwork", artwork.getTitle());
            String r = "UPDATE artwork SET title = '" + artwork.getTitle() + "', rating = '" + artwork.getRating() + "', comment = '" + artwork.getComment() +
                    "' WHERE artwork.id = " + idArtwork + ";";
            requestInsertUpdateDeleteInDB(r);
            r = "UPDATE category_has_artwork SET category_id = '" + idCat + "', artwork_id = '" + idArtwork + "'," +
                    " support_id = '" + idSupport + "', year_id = '" + idYear + "', origin_id = '" + idOrigin + "'," +
                    " creator_id = '" + idCreator + "', genre_id = '" + idGenre + "' " +
                    "WHERE category_has_artwork.artwork_id = " + idArtwork + ";";
            requestInsertUpdateDeleteInDB(r);
        }

    }

    public Integer selectId(String searchTable, String searchValue){
        String r;
        if(searchTable=="artwork"){
            r = "(SELECT id FROM " + searchTable + " WHERE title = '" + searchValue + "');";
        }else{
            r = "(SELECT id FROM " + searchTable + " WHERE value = '" + searchValue + "');";
        }
        ResultSet result = requestSelectOnDB(r);
        try {
            result.first();
            return result.getInt("id");
        } catch (SQLException e) {
            System.out.println("Problem in Select id");
            e.printStackTrace();
        }
        return 0;
    }

    public Integer selectIdYear(Integer year){
        String r = "(SELECT id FROM year WHERE value = '" + year + "');";
        ResultSet result = requestSelectOnDB(r);
        try {
            result.first();
            return result.getInt("id");
        } catch (SQLException e) {
            System.out.println("Problem in Select id");
            e.printStackTrace();
        }
        return 0;
    }


}
