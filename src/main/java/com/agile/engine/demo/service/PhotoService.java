package com.agile.engine.demo.service;

import com.agile.engine.demo.domain.Photo;
import com.agile.engine.demo.domain.Tag;
import com.agile.engine.demo.repository.PhotoRepository;
import com.agile.engine.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository,
                        TagRepository tagRepository) {
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }

    public Photo save(Photo photo) {
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : photo.getTags()) {
            Tag fromDb = tagRepository.getByTag(tag.getTag());
            if (fromDb == null) {
                fromDb = tagRepository.save(tag);
            }
            tags.add(fromDb);
        }
        photo.setTags(tags);
        return photoRepository.save(photo);
    }

    public Photo getById(String id) {
        return photoRepository.findAllByExternalId(id);
    }

    public List<Photo> search(MultiValueMap<String, String> multiValueMap) {
        return photoRepository.findAll(new Specification<Photo>() {
            @Override
            public Predicate toPredicate(Root<Photo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                for (Map.Entry<String, List<String>> set : multiValueMap.entrySet()) {
                    predicates.add(root.get(set.getKey()).in(set.getValue()));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        });
    }

    public Page<Photo> getPage(int page) {
        return photoRepository.findAll(PageRequest.of(page, 10));
    }

    public List<Photo> getAll() {
        return photoRepository.findAll();
    }
}
