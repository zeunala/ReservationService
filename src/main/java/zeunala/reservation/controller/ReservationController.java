package zeunala.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zeunala.reservation.dto.Reservation;
import zeunala.reservation.service.ReservationService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservationController {
	@Autowired
	ReservationService reservationService;

	@GetMapping("/")
	public String index() {
		return "redirect:/mainpage";
	}

	@GetMapping("/mainpage")
	public String mainpage() {
		return "mainpage";
	}

	@GetMapping(path="/detail")
	public String detail() {

		return "detail";
	}

	@GetMapping(path="/review")
	public String review() {

		return "review";
	}

	@GetMapping(path="/bookinglogin")
	public String bookinglogin() {

		return "bookinglogin";
	}

	@GetMapping(path="/myreservation")
	public String myreservation(HttpSession session) {
		if (session.getAttribute("loginEmail") != null) {
			return "myreservation";
		} else {
			return "bookinglogin"; // 로그인 되어있지 않은 상태에서 url로 접근시 로그인 페이지로 이동
		}
	}

	@PostMapping(path="/myreservation")
	public String myreservationPost(@RequestParam String resrv_email, HttpSession session) {
		if (session.getAttribute("loginEmail") == null && (reservationService.getReservations(resrv_email).size() > 0)) {
			// email로 검색 결과가 1건이상 있을 때만 세션에 저장됨(없어도 예약확인 페이지로는 이동되나 결과적으로 예약없음 화면만 보이게 된다)
			session.setAttribute("loginEmail", resrv_email);
		}

		return "myreservation";
	}

	@GetMapping(path="/reserve")
	public String reserve() {

		return "reserve";
	}

	@PostMapping(path="/review_write")
	public String review_write(@RequestParam String displayid, @RequestParam String reserveid, HttpSession session, ModelMap modelMap) {
		if (session.getAttribute("loginEmail") != null) {
			List<Reservation> reservationList = reservationService.getReservations((String)session.getAttribute("loginEmail"));

			// reservationList돌면서 displayid와 reserveid 모두 일치하는게 있으면 reviewWrite보여주고 아니면 비정상접근으로 간주해 mainpage로 돌려보냄
			for (Reservation i : reservationList) {
				if (i.getDisplayInfoId().equals(Integer.parseInt(displayid))
						&& i.getReservationInfoId().equals(Integer.parseInt(reserveid))) {
					modelMap.addAttribute("displayId", Integer.parseInt(displayid));
					modelMap.addAttribute("reserveId", Integer.parseInt(reserveid));
					return "reviewWrite";
				}
			}
			return "mainpage";
		} else {
			return "bookinglogin"; // 로그인 되어있지 않은 상태에서 url로 접근시 로그인 페이지로 이동
		}
	}
}
