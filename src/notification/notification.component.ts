import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {OverlayBadgeModule} from 'primeng/overlaybadge';
import {BehaviorSubject, debounceTime, Observable, Subject, switchMap,timer} from 'rxjs';
import {AsyncPipe, NgIf} from '@angular/common';
import {Popover} from 'primeng/popover';
import {InputGroup} from 'primeng/inputgroup';
import {InputGroupAddon} from 'primeng/inputgroupaddon';
import {NotificationService} from './notification.service';
import {MessageService} from 'primeng/api';
import {ToastModule} from 'primeng/toast';

@Component({
  selector: 'app-notification',
  imports: [OverlayBadgeModule, AsyncPipe, Popover, InputGroup, InputGroupAddon, ToastModule],
  templateUrl: './notification.component.html',
  standalone: true,
  styleUrl: './notification.component.css',
})
export class NotificationComponent implements OnInit{
  constructor(
    protected notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {

    this.notificationService.subscribe();
  }
  @ViewChild("popup") popupRef: Popover | undefined;

  showNotification($event: any) {
    this.popupRef?.toggle($event);
    this.notificationService.resetCount();
  }
}
