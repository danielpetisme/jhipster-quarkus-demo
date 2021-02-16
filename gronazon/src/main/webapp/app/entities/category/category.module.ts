import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GronazonSharedModule } from 'app/shared/shared.module';
import { CategoryComponent } from './category.component';
import { CategoryDetailComponent } from './category-detail.component';
import { CategoryUpdateComponent } from './category-update.component';
import { CategoryDeleteDialogComponent } from './category-delete-dialog.component';
import { categoryRoute } from './category.route';

@NgModule({
  imports: [GronazonSharedModule, RouterModule.forChild(categoryRoute)],
  declarations: [CategoryComponent, CategoryDetailComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent],
  entryComponents: [CategoryDeleteDialogComponent],
})
export class GronazonCategoryModule {}
