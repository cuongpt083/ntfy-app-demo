import {Component} from '@angular/core';
import {NotificationComponent} from '../notification/notification.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css',
  imports:[NotificationComponent,
    RouterOutlet]
})
export class AppComponent {
  title = 'notification';
}
