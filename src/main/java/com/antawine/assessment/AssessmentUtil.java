package com.antawine.assessment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssessmentUtil {

	private static final Logger logger = Logger.getLogger("AssessmentUtil");

	public AssessmentUtil() {
	}

	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("org.h2.Driver");
		} catch (final ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Driver not found", e);
		}
		try {
			dbConnection = DriverManager.getConnection("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:create.sql'", "", "");
		} catch (final SQLException e) {
			logger.log(Level.SEVERE, "SQL exception", e);
		}
		return dbConnection;
	}

	public static BigDecimal getEquivalentBigDecimal(final double amount) {
		return new BigDecimal(amount);
	}

	public static BigDecimal getEquivalentBigDecimal(final int amount) {
		return new BigDecimal(amount);
	}
}
