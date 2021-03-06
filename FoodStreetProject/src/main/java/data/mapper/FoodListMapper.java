package data.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import data.dto.MemberReviewDto;
import data.dto.RequestDto;
import data.paging.Criteria;

@Mapper
public interface FoodListMapper {

  public List<RequestDto> allList(Criteria cri);

  public List<RequestDto> categoryList(String category, int start, int end);

  public int totalCount();

  public int categoryTotalCount(String category);

  public RequestDto getNumList(int num);

  public List<RequestDto> imgNameList(int num);

  public List<MemberReviewDto> reviewList(int num);

  public RequestDto addressFind(int num);

}
