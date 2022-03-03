import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Authentication} from "./authentication/authentication";

const TOKEN_HEADER_KEY = 'Authorization';

const bypassUrl: string[] = ["oauth"];

@Injectable({
  providedIn: 'root',
})
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private auth: Authentication) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(this.needsAuth(req)){
      req = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.auth.getToken()) });
    }

    return next.handle(req);
  }

  private needsAuth(request: HttpRequest<unknown>): boolean {
    return bypassUrl.filter(url => request.url.includes(url)).length == 0;
  }
}
