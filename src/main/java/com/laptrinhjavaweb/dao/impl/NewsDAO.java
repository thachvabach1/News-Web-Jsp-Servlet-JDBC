package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import com.laptrinhjavaweb.dao.INewsDAO;
import com.laptrinhjavaweb.mapper.NewsMapper;
import com.laptrinhjavaweb.model.NewsModel;
import com.laptrinhjavaweb.paging.Pageble;
import org.apache.commons.lang.StringUtils;

public class NewsDAO extends AbstractDAO<NewsModel> implements INewsDAO{
	
	@Override
	public List<NewsModel> findByCategoryId(Long categoryId) {
		String sql = "SELECT * FROM news WHERE categoryid = ?";
		return query(sql, new NewsMapper(), categoryId);
	}

	@Override
	public Long save(NewsModel newsModel) {
//		String sql = "INSERT INTO news(title, content, categoryid) VALUES (?, ?, ?)";
		StringBuilder sql = new StringBuilder("INSERT INTO news(title, content,");
		sql.append(" thumbnail, shortdescription, categoryid, createddate, createdby)");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
		return insert(sql.toString(), newsModel.getTitle(), newsModel.getContent(),
				newsModel.getThumbnail(), newsModel.getShortDescription(), newsModel.getCategoryId(),
				newsModel.getCreatedDate(), newsModel.getCreatedBy());
	}

	@Override
	public NewsModel findOne(Long id) {
		String sql = "SELECT * FROM news WHERE id = ?";
		List<NewsModel> news = query(sql, new NewsMapper(), id);
		return news.isEmpty() ? null : news.get(0);
	}

	@Override
	public void update(NewsModel updateNews) {
		StringBuilder sql = new StringBuilder("UPDATE news SET title = ?, thumbnail = ?,");
		sql.append(" shortdescription = ?, content = ?, categoryid = ?,");
		sql.append(" createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? WHERE id = ?");
		update(sql.toString(), updateNews.getTitle(), updateNews.getThumbnail(), updateNews.getShortDescription(),
				updateNews.getContent(), updateNews.getCategoryId(), updateNews.getCreatedDate(),
				updateNews.getCreatedBy(), updateNews.getModifiedDate(), updateNews.getModifiedBy(), updateNews.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM news WHERE id = ?";
		update(sql, id);
	}

	@Override
	public List<NewsModel> findAll(Pageble pageble) {
		StringBuilder sql = new StringBuilder("SELECT * FROM news");
		// isNotBlank is check null and ""
		if (pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName()) && StringUtils.isNotBlank(pageble.getSorter().getSortBy())) {
			sql.append(" ORDER BY " + pageble.getSorter().getSortName() + " " + pageble.getSorter().getSortBy() + "");
		}
		if (pageble.getOffSet() != null && pageble.getLimit() != null) {
			sql.append(" LIMIT " + pageble.getOffSet() + ", " + pageble.getLimit() + "");
		}
		return query(sql.toString(), new NewsMapper());
	}

	@Override
	public int getTotalItem() {
		String sql = "SELECT count(*) FROM news";
		return count(sql);
	}
}
