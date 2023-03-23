import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AuthorizationMenuGuard } from '@core/guards/authorization-menu.guard';

// Component
import { AppMainComponent } from './accelengine-verona/app.main.component';
import { AppNotfoundComponent } from './accelengine-verona/pages/app.notfound.component';
import { AppUnderMaintenanceComponent } from './accelengine-verona/pages/app.maintenance.component';
import { AppLoginComponent } from './accelengine-verona/pages/app.login.component';


@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
            {
                path: '', component: AppMainComponent,
                children: [
                    { path: 'std', loadChildren: () => import('@std/std.module').then(m => m.StdModule), canActivate: [AuthorizationMenuGuard] },
                    { path: 'dashboard', loadChildren: () => import('@modules/module-dashboard/dashboard.module').then(m => m.DashboardModule), canActivate: [AuthorizationMenuGuard] },
                    { path: 'start', loadChildren: () => import('@modules/module-start/start.module').then(m => m.StartModule), canActivate: [AuthorizationMenuGuard] },
                ]
            },
            { path: 'login', component: AppLoginComponent },
            { path: 'notfound', component: AppNotfoundComponent },
            { path: 'maintenance', component: AppUnderMaintenanceComponent },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
