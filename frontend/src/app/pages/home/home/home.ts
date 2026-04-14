import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { SiteFooter } from '../site-footer/site-footer';
import { SiteNav } from '../site-nav/site-nav';

@Component({
  selector: 'app-home',
  imports: [RouterOutlet, SiteNav, SiteFooter],
  templateUrl: './home.html',
})
export class Home {}
