package com.antawine.assessment.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.antawine.assessment.AssessmentUtil;
import com.antawine.assessment.model.Item;

public class ItemRepositoryImpl implements ItemRepository {

	private static final Logger logger = Logger.getLogger("ItemRepositoryImpl");

	@Override
	public Item findByName(final String name) throws SQLException {
		final String SelectQuery = "SELECT * FROM ITEM WHERE NAME=?";
		try (Connection connection = AssessmentUtil.getDBConnection(); PreparedStatement stmt = connection.prepareStatement(SelectQuery);) {
			connection.setAutoCommit(false);
			stmt.setString(1, name);
			final ResultSet rs = stmt.executeQuery();
			Item item = null;
			while (rs.next()) {
				item = new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getBigDecimal("UNIT_PRICE"));
			}
			connection.commit();
			return item;
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "Exception: {0}", e.getLocalizedMessage());
			throw e;
		}
	}
}
