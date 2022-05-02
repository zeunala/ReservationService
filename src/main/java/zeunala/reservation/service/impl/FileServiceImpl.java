package zeunala.reservation.service.impl;

import zeunala.reservation.dao.FileInfoDao;
import zeunala.reservation.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	FileInfoDao fileInfoDao;

	@Override
	public String getSaveFileName(Integer fileInfoId) {
		return fileInfoDao.selectSaveFileNameById(fileInfoId);
	}

}
