import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BehaviorSubject, catchError, timer} from 'rxjs';
import {MessageService} from 'primeng/api';

@Injectable(
  {
    providedIn: 'root',
  }
)
export class NotificationService {
  debounceTime = 30000;
  unreadCount = 0;
  count$ = new BehaviorSubject<number>(0);

  constructor(public http: HttpClient, private messageService: MessageService) {
  }

  subscribe() {
    new EventSource("/notification/maker-msg/sse",).addEventListener('message', message => {
      this.messageResolve(message);
    });
  }
  messageResolve(message: MessageEvent<any>){
    console.log(message);
    if(message){
      this.count$.next(++this.unreadCount);
    }
    this.showMessage(message);
    console.log("Fetching data ok");
  }
  getCount() {
    return this.count$;
  }

  resetCount() {
    this.unreadCount = 0;
    this.count$.next(0);
  }

  private showMessage(data: any) {
    switch (data.status) {
      case 1:
        this.messageService.add({severity: 'success', summary: 'Success', detail: data.message});
        break;
      case 2:
        this.messageService.add({severity: 'error', summary: 'Error', detail: data.message});
        break;
      case 3:
        this.messageService.add({severity: 'warn', summary: 'Warn', detail: data.message});
        break;
      default:
        this.messageService.add({severity: 'info', summary: 'Info', detail: data.message});
        break;
    }
  }

  private getNotifications() {
    return this.http.get<any>("/notification/maker-msg/json", {
      headers: {'Content-Type': 'application/x-ndjson; charset=utf-8'}
    })
  }
}
