package dao;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import dao.DBConnection;
import model.Word;


public class WordDao extends DBConnection{

	public List<Word> getWordsOfAWorkline(int lineID) throws SQLException{
		String query = "SELECT * FROM Word WHERE workLineId = "+Integer.toString(lineID);
		return Extractors.extractWord(this.getStmt().executeQuery(query));
	}
	
	public List<Word> getWordsOfAWork(Integer workId) throws SQLException{
		String query = "SELECT * FROM Word WHERE workLineId in(select lineId from workLine where workId = "+Integer.toString(workId)+" )";
		return Extractors.extractWord(this.getStmt().executeQuery(query));
	}
}

