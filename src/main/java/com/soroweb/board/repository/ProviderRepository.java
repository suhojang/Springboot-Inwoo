package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.ProviderDto;

@Mapper
public interface ProviderRepository {

	public List<ProviderDto> findProviderListByBcDbnameAndPcatIdx(ProviderDto providerDto);
	public List<ProviderDto> findAll();
	public List<ProviderDto> findAllByAdmin();
	public ProviderDto findOne(ProviderDto provider);
	public ProviderDto findOneByAdmin(ProviderDto provider);
	public int insert(ProviderDto providerDto);
	public int update(ProviderDto provider);
	public int deleteByPvIdx(ProviderDto provider);
	public List<ProviderDto> findListByBcIsUseAndBcContinent(ProviderDto providerDto);
	public List<ProviderDto> findWorldPartners();
	public List<ProviderDto> findAllByPvLang(ProviderDto providerDto);
	public List<ProviderDto> findListForFooter();

}
