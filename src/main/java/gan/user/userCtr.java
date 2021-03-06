package gan.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class userCtr {
	private static final Integer cookieExpire = 60 * 60 * 24 * 30; // 1 month

	@Autowired
	userSvc userSvc;

	@RequestMapping(value = "/userPwUpdate") // 비밀번호 변경
	public String userpwupdate(userVO userInfo) {
		userSvc.updateUserInfo(userInfo);
		return "user/userPwUpdate";
	}
	
	@RequestMapping(value = "/userDel") // 회원 탈퇴
	public String userupdatesave(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();
		
		userSvc.deleteUser(userno);
		return "redirect:/userLogin";
	}
	
	@RequestMapping(value = "/userUpdateSave") // 회원가입 저장
	public String userupdatesave(userVO userInfo) {
		userSvc.updateUserInfo(userInfo);
		return "redirect:/studyList";
	}

	@RequestMapping(value = "/userUpdate") // 개인정보 수정
	public String userUpdate(HttpServletRequest request, ModelMap modelMap) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();

		userVO userInfo = userSvc.selectUserOne(userno);
		modelMap.addAttribute("userInfo", userInfo);
		return "user/userUpdateForm";
	}

	@RequestMapping(value = "/userInfo") // 개인정보 읽기
	public String userInfo(HttpServletRequest request, ModelMap modelMap) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();

		userVO userInfo = userSvc.selectUserOne(userno);
		modelMap.addAttribute("userInfo", userInfo);
		return "user/userInfo";
	}

	@RequestMapping(value = "/userJoin") // 회원가입 페이지 호출
	public String userjoin() {
		return "user/userJoin";
	}

	@RequestMapping(value = "/userJoinSave") // 회원가입 저장
	public String userjoinsave(userVO userInfo) {
		userSvc.insertUserJoin(userInfo);
		return "redirect:/userLogin";
	}
	@RequestMapping(value = "/userUpdatePwSave") // 비밀번호 변경 저장
	public String userUpdatePwSave(HttpServletRequest request, userVO userInfo) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();
		userInfo.setUserno(userno);
		
		userSvc.updatePw(userInfo);
		
		return "redirect:/studyList";
	}

	@RequestMapping(value = "/userJoinIdChk") // 회원가입 아이디체크
	@ResponseBody
	public String userjoinidchk(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		int chkNum = userSvc.selectUserIdChk(userid);
		return String.valueOf(chkNum);
	}
	
	@RequestMapping(value = "/userUpdatePwChk") // 비밀번호 변경
	@ResponseBody
	public String userupdaidchk(HttpServletRequest request, userVO userInfo) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();

		userInfo.setUserno(userno);
		
		int chkNum=userSvc.selectUserPwChk(userInfo);
		return String.valueOf(chkNum);
	}

	@RequestMapping(value = "/userLogin") // 로그인
	public String userlogin() {
		return "user/userLogin";
	}

	@RequestMapping(value = "/userLogout") // 로그아웃
	public String userlogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userno = session.getAttribute("userno").toString();

		userSvc.insertLogOut(userno);

		session.removeAttribute("userid");
		session.removeAttribute("userrole");
		session.removeAttribute("userno");
		session.removeAttribute("usernm");

		return "redirect:/userLogin";
	}

	@RequestMapping(value = "/userLoginChk") // 로그인 체
	public String memberLoginChk(HttpServletRequest request, HttpServletResponse response, LoginVO loginInfo,
			ModelMap modelMap) {

		userVO mdo = userSvc.selectUserLogin(loginInfo);

		if (mdo == null) {
			modelMap.addAttribute("msg", "로그인 할 수 없습니다.");
			return "user/userLogin_Fail";
		}

		userSvc.insertLogIn(mdo.getUserno());

		HttpSession session = request.getSession();

		session.setAttribute("userid", mdo.getUserid());
		session.setAttribute("userrole", mdo.getUserrole());
		session.setAttribute("userno", mdo.getUserno());
		session.setAttribute("usernm", mdo.getUsernm());

		if ("Y".equals(loginInfo.getRemember())) {
			set_cookie("sid", loginInfo.getUserid(), response);
		} else {
			set_cookie("sid", "", response);
		}

		return "redirect:/studyList";
	}

	/**
	 * 쿠키 저장.
	 */
	public static void set_cookie(String cid, String value, HttpServletResponse res) {

		Cookie ck = new Cookie(cid, value);
		ck.setPath("/");
		ck.setMaxAge(cookieExpire);
		res.addCookie(ck);
	}

	/**
	 * 쿠키 가져오기.
	 */
	public static String get_cookie(String cid, HttpServletRequest request) {
		String ret = "";

		if (request == null) {
			return ret;
		}

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return ret;
		}

		for (Cookie ck : cookies) {
			if (ck.getName().equals(cid)) {
				ret = ck.getValue();

				ck.setMaxAge(cookieExpire);
				break;
			}
		}
		return ret;
	}

}
