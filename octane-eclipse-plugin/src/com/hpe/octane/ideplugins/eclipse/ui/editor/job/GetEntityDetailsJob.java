package com.hpe.octane.ideplugins.eclipse.ui.editor.job;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.nga.sdk.model.FieldModel;
import com.hpe.adm.octane.services.CommentService;
import com.hpe.adm.octane.services.EntityService;
import com.hpe.adm.octane.services.MetadataService;
import com.hpe.adm.octane.services.exception.ServiceException;
import com.hpe.adm.octane.services.filtering.Entity;
import com.hpe.adm.octane.services.ui.FormLayout;
import com.hpe.adm.octane.services.util.Util;
import com.hpe.octane.ideplugins.eclipse.Activator;
import com.hpe.octane.ideplugins.eclipse.util.EntityFieldsConstants;

public class GetEntityDetailsJob extends Job {

    private long entityId;
    private boolean shoulShowPhase = false;
    private boolean areCommentsLoaded = false;
    private boolean areCommentsShown = false;
    private boolean wasEntityRetrived = false;
    private Entity entityType;
    private EntityModel retrivedEntity;
    private FieldModel currentPhase;
    private FormLayout octaneEntityForm;
    private Collection<EntityModel> possibleTransitions;
    private Collection<EntityModel> comments;

    private MetadataService metadataService = Activator.getInstance(MetadataService.class);
    private EntityService entityService = Activator.getInstance(EntityService.class);
    private CommentService commentService = Activator.getInstance(CommentService.class);

    public GetEntityDetailsJob(String name, Entity entityType, long entityId) {
        super(name);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
        try {
            retrivedEntity = entityService.findEntity(this.entityType, this.entityId);
            octaneEntityForm = metadataService.getFormLayoutForSpecificEntityType(Entity.getEntityType(retrivedEntity));
            getPhaseAndPossibleTransitions();
            getComments();
            wasEntityRetrived = true;
        } catch (ServiceException | UnsupportedEncodingException e) {
            wasEntityRetrived = false;
            e.printStackTrace();
        }
        monitor.done();
        return Status.OK_STATUS;
    }

    private void getPhaseAndPossibleTransitions() {
        if (Entity.MANUAL_TEST_RUN.equals(Entity.getEntityType(retrivedEntity))
                || Entity.TEST_SUITE_RUN.equals(Entity.getEntityType(retrivedEntity))) {
            shoulShowPhase = false;
        } else {
            shoulShowPhase = true;
            currentPhase = retrivedEntity.getValue("phase");
            Long currentPhaseId = Long.valueOf(Util.getUiDataFromModel(currentPhase, "id"));
            possibleTransitions = entityService.findPossibleTransitionFromCurrentPhase(Entity.getEntityType(retrivedEntity), currentPhaseId);
        }
    }

    private void getComments() {
        if (Entity.TASK.equals(Entity.getEntityType(retrivedEntity)) || Entity.MANUAL_TEST_RUN.equals(Entity.getEntityType(retrivedEntity))
                || Entity.TEST_SUITE_RUN.equals(Entity.getEntityType(retrivedEntity))) {
            areCommentsShown = false;
        } else {
            areCommentsShown = true;
            comments = commentService.getComments(retrivedEntity);
            areCommentsLoaded = true;
        }
    }

    public String getCommentsForCurrentEntity() {
        StringBuilder commentsBuilder = new StringBuilder();
        if (!comments.isEmpty()) {
            for (EntityModel comment : comments) {
                String commentsPostTime = Util.getUiDataFromModel(comment.getValue(EntityFieldsConstants.FIELD_CREATION_TIME));
                String userName = Util.getUiDataFromModel(comment.getValue(EntityFieldsConstants.FIELD_AUTHOR), "full_name");
                String commentLine = Util.getUiDataFromModel(comment.getValue(EntityFieldsConstants.FIELD_COMMENT_TEXT));
                String currentText = commentsPostTime + " <b>" + userName + ":</b> <br>" + commentLine + "<hr>";
                commentsBuilder.append(currentText);
            }
        }
        return commentsBuilder.toString();
    }

    public boolean areCommentsLoaded() {
        return areCommentsLoaded;
    }

    public boolean shouldCommentsBeShown() {
        return areCommentsShown;
    }

    public boolean wasEntityRetrived() {
        return wasEntityRetrived;
    }

    public EntityModel getEntiyData() {
        return retrivedEntity;
    }

    public FieldModel getCurrentPhase() {
        return currentPhase;
    }

    public FormLayout getFormForCurrentEntity() {
        return octaneEntityForm;
    }

    public Collection<EntityModel> getPossibleTransitionsForCurrentEntity() {
        if (possibleTransitions.isEmpty()) {
            possibleTransitions.add(new EntityModel("target_phase", "No transition"));
        }
        return possibleTransitions;
    }

    public boolean shouldShowPhase() {
        return shoulShowPhase;
    }

}