package com.antawine.assessment.persistence;

import java.sql.SQLException;

public interface Repository<T> {
	T findByName(String name) throws SQLException;
}
