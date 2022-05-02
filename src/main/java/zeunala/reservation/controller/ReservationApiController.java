package zeunala.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zeunala.reservation.dto.CommentResult;
import zeunala.reservation.dto.ReservationByEmail;
import zeunala.reservation.dto.ReservationParam;
import zeunala.reservation.dto.ReservationResult;
import zeunala.reservation.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(path="/api")
public class ReservationApiController {
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/reservations")
	public ReservationByEmail reservations(@RequestParam String reservationEmail) {
		ReservationByEmail result = new ReservationByEmail();
		
		result.setReservations(reservationService.getReservations(reservationEmail));
		result.setSize(reservationService.getReservations(reservationEmail).size());
		
		return result;
	}
	
	@PostMapping("/reservations")
	public ReservationResult addReservation(@RequestBody ReservationParam reservationParam) {
		return reservationService.addReservation(reservationParam);
	}
	
	@PutMapping("/reservations/{reservationId}")
	public ReservationResult cancelReservation(@PathVariable Integer reservationId) {
		return reservationService.cancelReservation(reservationId);
	}

	@PostMapping(path="/reservations/{reservationInfoId}/comments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CommentResult addComment(@PathVariable Integer reservationInfoId,
									@RequestParam("comment") String comment,
									@RequestParam("productId") Integer productId,
									@RequestParam("score") Double score,
									@RequestParam(required=false) MultipartFile attachedImage,
									HttpServletRequest request) throws IOException {
		final String IMAGE_COMMENT_PATH = request.getServletContext().getRealPath(("/img_comment/"));
		String imageFileName = null;

		// score, comment 길이 유효성 검사
		if (score < 0 || score > 5 || comment.length() < 5 || comment.length() > 400) {
			return null;
		}

		// 첨부된 파일이 있을 경우 업로드
		if (attachedImage != null) {
			imageFileName = attachedImage.getOriginalFilename();

			// 중복 파일명 처리
			File targetFile = new File(IMAGE_COMMENT_PATH + imageFileName);
			int fileNumber = 1;
			while (targetFile.exists()) {
				String originalImageFileName = attachedImage.getOriginalFilename();
				fileNumber++;
				String newImageFileName = originalImageFileName.substring(0, originalImageFileName.lastIndexOf("."))
						+ " (" + Integer.toString(fileNumber) + ")"
						+ originalImageFileName.substring(originalImageFileName.lastIndexOf("."));
				targetFile = new File(IMAGE_COMMENT_PATH + newImageFileName);
				imageFileName = newImageFileName;
			}

			// 확장자 유효성 검사
			String fileNameExtension = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
			if (!fileNameExtension.equals("jpg") && !fileNameExtension.equals("jpeg") && !fileNameExtension.equals("png")) {
				return null;
			}

			attachedImage.transferTo(new File(IMAGE_COMMENT_PATH + imageFileName));
		}
		return reservationService.addComment(reservationInfoId, comment, productId, score, imageFileName);
	}
}
