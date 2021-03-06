package data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import data.dto.MemberDto;
import data.dto.Role;
import data.service.JoinService;
import data.validator.IdCheckValidator;
import data.validator.NicknameCheckValidator;
import data.validator.PwCheckValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class JoinController {

  private final JoinService service;
  private final IdCheckValidator idValidator;
  private final NicknameCheckValidator nicknameValidator;
  private final PwCheckValidator pwValidator;

  // 커스텀 유효성 검사!
  @InitBinder
  public void validatorBinder(WebDataBinder binder) {
    binder.addValidators(idValidator);
    binder.addValidators(nicknameValidator);
    binder.addValidators(pwValidator);
  }

  @GetMapping("/join")
  public String join() {
    return "/users/join";
  }

  @GetMapping("/join/form")
  public String form() {
    return "/users/join_form";
  }

  // 회원가입
  @PostMapping("/join/success")
  public String insert(String id, MultipartFile file, @Valid MemberDto dto, Errors errors,
      Model model) throws IOException {

    // 유효성 검사
    if (errors.hasErrors()) {
      // 회원가입 실패시 작성한 정보 유지!
      model.addAttribute("MemberDto", dto);

      // 유효성 실패한 항목들에 대한 메세지!
      Map<String, String> validatorResult = service.validateHandling(errors);
      for (String key : validatorResult.keySet()) {
        model.addAttribute(key, validatorResult.get(key));
      }
      return "/users/join_form";
    }
    service.insertMember(dto, file);
    String name = service.getFindName(id);
    model.addAttribute("name", name);
    return "/users/join_success";
  }

  // 운영진 계정 생성
  @PostMapping("/executive/success")
  public void insertExecutive(String id, MultipartFile file, @Valid MemberDto dto, Errors errors,
      Model model, HttpServletResponse response) throws IOException {

    // 유효성 검사
    if (errors.hasErrors()) {
      // 회원가입 실패시 작성한 정보 유지!
      model.addAttribute("MemberDto", dto);

      // 유효성 실패한 항목들에 대한 메세지!
      Map<String, String> validatorResult = service.validateHandling(errors);
      for (String key : validatorResult.keySet()) {
        model.addAttribute(key, validatorResult.get(key));
      }
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script> location.href='/admin/executive/form';</script>");
    }
    service.insertMember(dto, file);

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<script>alert('운영진 계정이 등록되었습니다!'); location.href='/admin/mypage';</script>");
  }

  // 회원정보 수정
  @PostMapping("/join/update")
  public void update(int num, MemberDto dto, MultipartFile file, Errors errors, Model model,
      HttpServletResponse response, HttpSession session) throws IOException {

    MemberDto member = (MemberDto) session.getAttribute("member");
    Role privider = member.getAuth_provider();

    if (privider.equals(Role.ADMIN)) {

      service.updateMember(dto, num, file);

      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println(
          "<script>alert('운영자 계정이 수정되었습니다!'); location.href='/admin/executive/list';</script>");
    } else if (privider.equals(Role.USER) || privider.equals(Role.SOCIAL)) {

      service.updateMember(dto, num, file);

      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>alert('회원님의 계정이 수정되었습니다!'); location.href='/mypage';</script>");
    } else {
      service.updateMember(dto, num, file);

      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println(
          "<script>alert('운영진님의 계정이 수정되었습니다!'); location.href='/executive/mypage';</script>");
    }
  }

  // 아이디 중복확인
  @ResponseBody
  @RequestMapping("/idCheck")
  public int idCheck(@RequestBody String id) {

    int cnt = service.idCheck(id);
    return cnt;
  }

  // 닉네임 중복확인
  @ResponseBody
  @RequestMapping("/nicknameCheck")
  public int nicknameCheck(@RequestBody String nickname) {

    int cnt = service.nicknameCheck(nickname);
    return cnt;
  }

  // 이메일 중복확인
  @ResponseBody
  @RequestMapping("/emailCheck")
  public int emailCheck(@RequestBody String email) {

    int cnt = service.emailCheck(email);
    return cnt;
  }

  // 인증번호
  @ResponseBody
  @RequestMapping("/check/sms")
  public String sms(@RequestBody String hp) {

    Random rd = new Random();
    String randomNum = "";
    for (int i = 0; i < 6; i++) {
      String ran = Integer.toString(rd.nextInt(10));
      randomNum += ran;
    }

    service.phoneNumberCertified(hp, randomNum);
    return randomNum;
  }

}
