import { ApplicationConfig } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app-routing.module'; 

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(), //  This is required for HttpClient to work
    provideRouter(routes)
  ]
};
