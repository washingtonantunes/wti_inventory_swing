package model.dao;

public static SellerDao createSellerDao() {
	return new SellerDaoJDBC(DB.getConnection());
}
