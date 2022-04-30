package zeunala.reservation.dao;

public class FileInfoDaoSqls {
	public static final String SELECT_SAVE_FILE_NAME_BY_ID = "SELECT save_file_name FROM file_info WHERE id = :fileInfoId;";
}
