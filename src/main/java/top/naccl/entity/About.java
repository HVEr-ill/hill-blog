package top.naccl.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 关于我
 * @Author: Naccl
 * @Date: 2020-08-31
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class About {
	private Long id;
	private String nameEn;//about的英文名字
	private String nameZh;//about的中文名字
	private String value;//内容
}
