package data.service;

import java.util.List;
import org.springframework.stereotype.Service;
import data.dto.MemberReviewDto;
import data.dto.RequestDto;
import data.mapper.FoodListMapper;
import data.paging.Criteria;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FoodListService {

  private final FoodListMapper mapper;

  public List<RequestDto> allList(Criteria cri) {
    return mapper.allList(cri);
  }

  public List<RequestDto> categoryList(String category, int start, int end) {
    return mapper.categoryList(category, start, end);
  }

  public int totalCount() {
    return mapper.totalCount();
  }

  public int categoryTotalCount(String category) {
    return mapper.categoryTotalCount(category);
  }

  public RequestDto getNumList(int num) {
    return mapper.getNumList(num);
  }

  public List<RequestDto> imgNameList(int num) {
    return mapper.imgNameList(num);
  }

  public List<MemberReviewDto> reviewList(int num) {
    return mapper.reviewList(num);
  }

  public RequestDto addressFind(int num) {
    return mapper.addressFind(num);
  }

}
