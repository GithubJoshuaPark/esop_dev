package com.soro.esop.entity.listeners;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

import com.soro.esop.entity.base.History;

import jakarta.persistence.*;

public class HistoryListener {

  private static Supplier<String> user = (()->StringUtils.EMPTY);

  public void setUser(Supplier<String> user) {
      HistoryListener.user = user;
  }

  public String getUserId() {
      return user.get();
  }

  private static Consumer<Object> prePersist = ((Object object)->{  });

  public void setPrePersist(Consumer<Object> prePersist) {
      HistoryListener.prePersist = prePersist;
  }

  @PrePersist
  public void prePersist(final Object object) {
      if (object instanceof History) {
          History history = (History) object;
          history.created(user.get());
          prePersist.accept(object);
      }
  }

  private static Consumer<Object> postPersist = ((Object object)->{  });

  public void setPostPersist(Consumer<Object> postPersist) {
      HistoryListener.postPersist = postPersist;
  }

  @PostPersist
  public void PostPersist(final Object object) {
      if (object instanceof History) {
          postPersist.accept(object);
      }
  }

  private static Consumer<Object> preUpdate = ((Object object)->{  });

  public void setPreUpdate(Consumer<Object> preUpdate) {
      HistoryListener.preUpdate = preUpdate;
  }

  @PreUpdate
  public void preUpdate(final Object object) {
      if (object instanceof History) {
          History history = (History) object;
          history.modified(user.get());
          preUpdate.accept(object);
      }
  }

  private static Consumer<Object> postUpdate = ((Object object)->{  });

  public void setPostUpdate(Consumer<Object> postUpdate) {
      HistoryListener.postUpdate = postUpdate;
  }

  @PostUpdate
  public void PostUpdate(final Object object) {
      if (object instanceof History) {
          postUpdate.accept(object);
      }
  }

  private static Consumer<Object> preRemove = ((Object object)->{  });

  public void setPreRemove(Consumer<Object> preRemove) {
      HistoryListener.preRemove = preRemove;
  }

  @PreRemove
  public void preRemove(final Object object) {
      if (object instanceof History) {
          preRemove.accept(object);
      }
  }

  private static Consumer<Object> postRemove = ((Object object)->{  });

  public void setPostRemove(Consumer<Object> postRemove) {
      HistoryListener.postRemove = postRemove;
  }

  @PostRemove
  public void PostRemove(final Object object) {
      if (object instanceof History) {
          postRemove.accept(object);
      }
  }
}